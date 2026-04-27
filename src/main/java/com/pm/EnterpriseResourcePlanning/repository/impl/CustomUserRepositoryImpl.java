package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.enums.UserStatus;
import com.pm.EnterpriseResourcePlanning.repository.CustomUserRepository;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("users");
    private final Field<UUID> idField = field("id", UUID.class);
    private final Field<String> fullNameField = field("full_name", String.class);
    private final Field<String> usernameField = field("username", String.class);
    private final Field<String> passwordField = field("password", String.class);
    private final Field<String> phoneNumberField = field("phone_number", String.class);
    private final Field<Object> statusField = DSL.field("status", DSL.name("user_status"));

    public CustomUserRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Optional<UserEntity> saveUser(String fullName, String name, String password, String phone) {
        var record = dsl.insertInto(tableName)
                .columns(fullNameField, usernameField, passwordField, phoneNumberField, statusField)
                .values(fullName, name, password, phone, DSL.field("?::user_status", UserStatus.ACTIVE.name()))
                .returning(idField, fullNameField, usernameField, passwordField, phoneNumberField, statusField)
                .fetchOne();

        System.out.println(record);

        if (record == null) {
            return Optional.empty();
        }

        UserEntity user = UserEntity.builder()
                .id(record.get(idField))
                .fullName(record.get(fullNameField))
                .username(record.get(usernameField))
                .password(record.get(passwordField))
                .phoneNumber(record.get(phoneNumberField))
                .userStatus(UserStatus.valueOf(String.valueOf(record.get(statusField))))
                .build();

        return Optional.of(user);
    }
}
