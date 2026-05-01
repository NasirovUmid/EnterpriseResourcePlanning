package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ClientEntity;
import com.pm.EnterpriseResourcePlanning.enums.ClientType;
import com.pm.EnterpriseResourcePlanning.repository.CustomClientRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class CustomClientRepositoryImpl implements CustomClientRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("clients");
    private final Field<String> nameFIeld = field("full_name", String.class);
    private final Field<String> phoneField = field("phone", String.class);
    private final Field<Object> typeField = field("type", DSL.name("clients_type"));
    private final Field<UUID> idField = field("id", UUID.class);

    @Override
    public Optional<ClientEntity> saveClient(String fullname, String phone, ClientType type) {

        var record = dsl.insertInto(tableName)
                .columns(nameFIeld, phoneField, typeField)
                .values(fullname, phone, DSL.field("?::clients_type", type))
                .returning(nameFIeld, phoneField, typeField)
                .fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(ClientEntity.builder()
                .id(record.get(idField))
                .fullName(record.get(nameFIeld))
                .phone(record.get(phoneField))
                .type(ClientType.valueOf(String.valueOf(record.get(typeField))))
                .build());
    }

}
