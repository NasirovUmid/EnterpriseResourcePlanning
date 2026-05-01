(function () {
    const STORAGE_KEYS = {
        access: "erp_access_token",
        refresh: "erp_refresh_token",
        userId: "erp_user_id",
        email: "erp_user_email",
        roles: "erp_user_roles"
    };

    function parseJwt(token) {
        if (!token) {
            return null;
        }

        try {
            const payload = token.split(".")[1];
            const normalized = payload.replace(/-/g, "+").replace(/_/g, "/");
            return JSON.parse(atob(normalized));
        } catch (error) {
            console.error("JWT parse error", error);
            return null;
        }
    }

    function formatError(error) {
        if (!error) {
            return "Unknown error";
        }

        if (typeof error === "string") {
            return error;
        }

        if (error.message) {
            return error.message;
        }

        if (Array.isArray(error.errors) && error.errors.length > 0) {
            return error.errors.join(", ");
        }

        try {
            return JSON.stringify(error);
        } catch (jsonError) {
            return "Unexpected response";
        }
    }

    function getApiBase() {
        return window.location.origin;
    }

    function getSession() {
        return {
            accessToken: localStorage.getItem(STORAGE_KEYS.access) || "",
            refreshToken: localStorage.getItem(STORAGE_KEYS.refresh) || "",
            userId: localStorage.getItem(STORAGE_KEYS.userId) || "",
            email: localStorage.getItem(STORAGE_KEYS.email) || ""
        };
    }

    function getStoredRoles() {
        const raw = localStorage.getItem(STORAGE_KEYS.roles);
        if (!raw) {
            return [];
        }

        try {
            const parsed = JSON.parse(raw);
            return Array.isArray(parsed) ? parsed.map(normalizeRole).filter(Boolean) : [];
        } catch (error) {
            console.error("Roles parse error", error);
            return [];
        }
    }

    function saveRoles(roles) {
        localStorage.setItem(STORAGE_KEYS.roles, JSON.stringify(Array.isArray(roles) ? roles.map(normalizeRole).filter(Boolean) : []));
    }

    function normalizeRole(roleName) {
        return String(roleName || "").toUpperCase().replace(/^ROLE_/, "");
    }

    function saveSession(payload, extra) {
        localStorage.removeItem(STORAGE_KEYS.roles);

        if (payload && payload.accessToken) {
            localStorage.setItem(STORAGE_KEYS.access, payload.accessToken);
            const claims = parseJwt(payload.accessToken);
            if (claims && claims.sub) {
                localStorage.setItem(STORAGE_KEYS.email, claims.sub);
            }
        }

        if (payload && payload.refreshToken) {
            localStorage.setItem(STORAGE_KEYS.refresh, payload.refreshToken);
        }

        const userId = extra && extra.userId ? extra.userId : payload && payload.userId ? payload.userId : "";
        if (userId) {
            localStorage.setItem(STORAGE_KEYS.userId, userId);
        }
    }

    function clearSession() {
        Object.values(STORAGE_KEYS).forEach(function (key) {
            localStorage.removeItem(key);
        });
    }

    function redirectToLogin() {
        const currentPath = window.location.pathname || "";
        if (!currentPath.endsWith("/pages/login.html")) {
            window.location.href = "/pages/login.html";
        }
    }

    async function readJson(response) {
        const text = await response.text();
        if (!text) {
            return null;
        }

        try {
            return JSON.parse(text);
        } catch (error) {
            return text;
        }
    }

    async function refreshSession() {
        const session = getSession();
        if (!session.refreshToken) {
            clearSession();
            redirectToLogin();
            throw new Error("Refresh token not found");
        }

        const response = await fetch(getApiBase() + "/auth/refresh", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({refreshToken: session.refreshToken})
        });

        const payload = await readJson(response);
        if (!response.ok) {
            clearSession();
            redirectToLogin();
            throw payload || new Error("Unable to refresh token");
        }

        saveSession(payload, {userId: session.userId});
        return payload;
    }

    function shouldTryRefresh(response) {
        return response.status === 401 || response.status === 403;
    }

    async function authorizedFetch(url, options) {
        const session = getSession();
        if (!session.accessToken && !session.refreshToken) {
            redirectToLogin();
            throw new Error("Session not found");
        }

        const headers = new Headers(options && options.headers ? options.headers : {});

        if (session.accessToken) {
            headers.set("Authorization", "Bearer " + session.accessToken);
        }

        let response = await fetch(url, Object.assign({}, options, {headers: headers}));

        if (shouldTryRefresh(response) && session.refreshToken) {
            await refreshSession();
            const nextSession = getSession();
            headers.set("Authorization", "Bearer " + nextSession.accessToken);
            response = await fetch(url, Object.assign({}, options, {headers: headers}));
        }

        if (shouldTryRefresh(response)) {
            clearSession();
            redirectToLogin();
        }

        return response;
    }

    async function logoutSession() {
        const session = getSession();

        if (session.refreshToken) {
            await fetch(getApiBase() + "/auth/log-out", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({refreshToken: session.refreshToken})
            });
        }

        clearSession();
    }

    function setStatus(targetId, message, type) {
        const element = document.getElementById(targetId);
        if (!element) {
            return;
        }

        element.textContent = message || "";
        element.classList.remove("success", "error");

        if (type === "success") {
            element.classList.add("success");
        }

        if (type === "error") {
            element.classList.add("error");
        }
    }

    function fillSessionSummary(prefix) {
        const session = getSession();
        const claims = parseJwt(session.accessToken);

        const mapping = {
            email: session.email || (claims && claims.sub) || "Not available",
            userId: session.userId || "Not saved",
            tokenType: (claims && claims.type) || "Not available",
            expiresAt: claims && claims.exp ? new Date(claims.exp * 1000).toLocaleString() : "Not available"
        };

        Object.entries(mapping).forEach(function (entry) {
            const name = entry[0];
            const value = entry[1];
            const element = document.getElementById(prefix + "-" + name);
            if (element) {
                element.textContent = value;
            }
        });

        const rolesElement = document.getElementById(prefix + "-roles");
        if (rolesElement) {
            const roles = getStoredRoles();
            rolesElement.textContent = roles.length ? roles.join(", ") : "Not loaded";
        }
    }

    function getUserIdFromPage() {
        const queryId = new URLSearchParams(window.location.search).get("id");
        if (queryId) {
            localStorage.setItem(STORAGE_KEYS.userId, queryId);
            return queryId;
        }

        const input = document.getElementById("userId");
        if (input && input.value.trim()) {
            localStorage.setItem(STORAGE_KEYS.userId, input.value.trim());
            return input.value.trim();
        }

        return getSession().userId;
    }

    function hasRole(roleName) {
        return getStoredRoles().includes(roleName);
    }

    function hasAnyRole(roleNames) {
        return roleNames.some(hasRole);
    }

    async function fetchCurrentRoles() {
        const userId = getUserIdFromPage();
        if (!userId) {
            throw new Error("User ID is required to load roles");
        }

        const response = await authorizedFetch(getApiBase() + "/users/" + encodeURIComponent(userId) + "/roles", {
            method: "GET"
        });
        const payload = await readJson(response);

        if (!response.ok) {
            throw payload || new Error("Unable to load user roles");
        }

        const roles = Array.isArray(payload)
            ? payload.map(function (role) { return role && role.name ? normalizeRole(role.name) : ""; }).filter(Boolean)
            : [];

        saveRoles(roles);
        return roles;
    }

    async function ensureRolesLoaded() {
        const roles = getStoredRoles();
        if (roles.length > 0) {
            return roles;
        }

        return fetchCurrentRoles();
    }

    function applyRoleVisibility(roles) {
        document.querySelectorAll("[data-visible-for]").forEach(function (element) {
            const required = (element.getAttribute("data-visible-for") || "")
                .split(",")
                .map(function (value) { return normalizeRole(value.trim()); })
                .filter(Boolean);

            if (required.length === 0) {
                element.hidden = false;
                return;
            }

            element.hidden = !required.some(function (role) {
                return roles.includes(role);
            });
        });

        document.querySelectorAll("[data-hidden-for]").forEach(function (element) {
            const forbidden = (element.getAttribute("data-hidden-for") || "")
                .split(",")
                .map(function (value) { return normalizeRole(value.trim()); })
                .filter(Boolean);

            if (forbidden.some(function (role) { return roles.includes(role); })) {
                element.hidden = true;
            }
        });
    }

    async function initializeRoleView() {
        const roles = await ensureRolesLoaded();
        applyRoleVisibility(roles);
        return roles;
    }

    function hideRoleControlledElementsUntilLoaded() {
        document.querySelectorAll("[data-visible-for]").forEach(function (element) {
            element.hidden = true;
        });
    }

    async function guardPage(roleNames, fallbackPath) {
        const roles = await initializeRoleView();
        if (!roleNames || roleNames.length === 0) {
            return roles;
        }

        if (!roleNames.some(function (role) { return roles.includes(role); })) {
            window.location.href = fallbackPath || "/pages/profile.html";
            throw new Error("Access denied for this page");
        }

        return roles;
    }

    window.erpAuth = {
        STORAGE_KEYS,
        applyRoleVisibility,
        authorizedFetch,
        clearSession,
        ensureRolesLoaded,
        fetchCurrentRoles,
        fillSessionSummary,
        formatError,
        getApiBase,
        getSession,
        getStoredRoles,
        getUserIdFromPage,
        guardPage,
        hasAnyRole,
        hasRole,
        hideRoleControlledElementsUntilLoaded,
        initializeRoleView,
        logoutSession,
        parseJwt,
        readJson,
        redirectToLogin,
        refreshSession,
        normalizeRole,
        saveRoles,
        saveSession,
        setStatus
    };

    hideRoleControlledElementsUntilLoaded();
})();
