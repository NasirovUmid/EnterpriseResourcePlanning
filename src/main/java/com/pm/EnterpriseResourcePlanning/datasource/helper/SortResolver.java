package com.pm.EnterpriseResourcePlanning.datasource.helper;

import com.pm.EnterpriseResourcePlanning.enums.SortType;
import org.springframework.data.domain.Sort;

import java.util.Map;
import java.util.Set;

public class SortResolver {

    private static final Map<SortType, String> DEFAULT = Map.of(
            SortType.USER, "fullName",
            SortType.PROJECT, "name",
            SortType.PRODUCT, "name",
            SortType.CONTRACT, "startDate",
            SortType.ORGANIZATION, "name",
            SortType.PERMISSION, "name",
            SortType.CLIENT, "fullName",
            SortType.SALES, "totalPrice"
    );

    private static final Map<SortType, Set<String>> ALLOWED = Map.of(

            SortType.USER, Set.of("id", "fullName", "username", "phoneNumber", "userStatus"),
            SortType.CLIENT, Set.of("id", "fullName", "phone", "type"),
            SortType.PRODUCT, Set.of("id", "name", "price", "unit", "status"),
            SortType.PROJECT, Set.of("id", "name", "status"),
            SortType.CONTRACT, Set.of("id", "startDate", "endDate", "contractNumber", "amount"),
            SortType.ORGANIZATION, Set.of("id", "name", "inn", "address"),
            SortType.PERMISSION, Set.of("id", "name"),
            SortType.SALES,Set.of("id","totalPrice","date","status")
    );

    public static Sort resolver(SortType sortType,String value){
        String defaultField = DEFAULT.getOrDefault(sortType,"id");

        if (value == null || !value.contains(",")){
            return Sort.by(defaultField).ascending();
        }

        String[] parts = value.split(",");
        String field = parts[0].trim();
        String direction = parts[1].trim();

        if (!ALLOWED.getOrDefault(sortType,Set.of("id")).contains(field)){
            field = defaultField;
        }

        return "desc".equalsIgnoreCase(direction) ? Sort.by(field).descending() : Sort.by(field).ascending();
    }

}
