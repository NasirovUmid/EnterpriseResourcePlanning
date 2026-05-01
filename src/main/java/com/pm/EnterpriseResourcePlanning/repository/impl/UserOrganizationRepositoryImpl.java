package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.enums.UserStatus;
import com.pm.EnterpriseResourcePlanning.repository.UserOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class UserOrganizationRepositoryImpl implements UserOrganizationRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("user_organization");
    private final Field<UUID> userField = field("user_id", UUID.class);
    private final Field<UUID> organizationField = field("organization_id", UUID.class);
    private final Table<?> uTable = table("users");
    private final Table<?> orgTable = table("organizations");
    private final Field<String> nameField = field("name", String.class);
    private final Field<String> innField = field("inn", String.class);
    private final Field<String> addressField = field("address", String.class);
    private final Field<UUID> idField = field("id", UUID.class);

    @Override
    public void saveUserOrganization(UUID userId, UUID organizationId) {

        System.out.println("VALUES = " + userId + "  sdssfdsfds" + organizationId);

        dsl.insertInto(tableName)
                .columns(userField, organizationField)
                .values(userId, organizationId)
                .execute();
    }

    @Override
    public boolean exists(UUID userId, UUID organizationId) {
        return dsl.fetchExists(dsl.selectOne()
                .from(tableName)
                .where(userField.eq(userId))
                .and(organizationField.eq(organizationId)));
    }

    @Transactional
    @Override
    public void removeUserOrganizationLink(UUID userId, UUID organizationId) {
        dsl.deleteFrom(tableName)
                .where(userField.eq(userId))
                .and(organizationField.eq(organizationId))
                .execute();
    }

    @Override
    public List<UserEntity> getOrganizationUsers(UUID organizationId) {
        return dsl.select(uTable.fields())
                .from(uTable)
                .join(tableName).on(field("users.id").eq(userField))
                .where(organizationField.eq(organizationId))
                .fetch(record -> UserEntity.builder()
                        .id(record.get(idField))
                        .fullName(record.get(field("full_name", String.class)))
                        .username(record.get(field("username", String.class)))
                        .phoneNumber(record.get(field("phone_number", String.class)))
                        .userStatus(UserStatus.valueOf(String.valueOf(record.get(field("status", String.class)))))
                        .build());
    }

    @Override
    public List<OrganizationEntity> getUserOrganizations(UUID userId) {

        return dsl.select(orgTable.fields()) // Выбираем все поля из таблицы организаций
                .from(orgTable)
                .join(tableName)
                .on(field(name("organizations", "id"))
                        .eq(field(name("user_organization", "organization_id"))))
                .where(field(name("user_organization", "user_id"), UUID.class).eq(userId))
                .fetch(record ->
                        OrganizationEntity.builder()
                                .id(record.get(idField))
                                .name(record.get(nameField))
                                .inn(record.get(innField))
                                .address(record.get(addressField))
                                .build());
    }
}
