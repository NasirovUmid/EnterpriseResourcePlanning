package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import com.pm.EnterpriseResourcePlanning.repository.CustomOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class CustomOrganizationRepositoryImpl implements CustomOrganizationRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("organizations");
    private final Field<String> nameField = field("name", String.class);
    private final Field<String> innField = field("inn", String.class);
    private final Field<String> addressField = field("address", String.class);
    private final Field<UUID> idField = field("id", UUID.class);


    @Override
    public Optional<OrganizationEntity> save(String name, String inn, String address) {

        var record = dsl.insertInto(tableName)
                .columns(nameField, innField, addressField)
                .values(name, inn, address)
                .returning(idField, nameField, innField, addressField)
                .fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        OrganizationEntity organization = OrganizationEntity.builder()
                .id(record.get(idField))
                .name(record.get(nameField))
                .inn(record.get(innField))
                .address(record.get(addressField))
                .build();

        return Optional.of(organization);
    }
}
