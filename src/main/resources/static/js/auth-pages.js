(function () {
    const STORAGE_KEYS = {
        access: "erp_access_token",
        refresh: "erp_refresh_token",
        userId: "erp_user_id",
        email: "erp_user_email"
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

    function saveSession(payload, extra) {
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
        Object.values(STORAGE_KEYS).forEach((key) => localStorage.removeItem(key));
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
            throw payload || new Error("Unable to refresh token");
        }

        saveSession(payload, {userId: session.userId});
        return payload;
    }

    async function authorizedFetch(url, options) {
        const session = getSession();
        const headers = new Headers(options && options.headers ? options.headers : {});

        if (session.accessToken) {
            headers.set("Authorization", "Bearer " + session.accessToken);
        }

        let response = await fetch(url, {...options, headers});

        if (response.status === 401 && session.refreshToken) {
            await refreshSession();
            const nextSession = getSession();
            headers.set("Authorization", "Bearer " + nextSession.accessToken);
            response = await fetch(url, {...options, headers});
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

        Object.entries(mapping).forEach(([name, value]) => {
            const element = document.getElementById(prefix + "-" + name);
            if (element) {
                element.textContent = value;
            }
        });
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

    window.erpAuth = {
        STORAGE_KEYS,
        authorizedFetch,
        clearSession,
        fillSessionSummary,
        formatError,
        getApiBase,
        getSession,
        getUserIdFromPage,
        parseJwt,
        readJson,
        refreshSession,
        saveSession,
        setStatus,
        logoutSession
    };
})();
