package com.pm.EnterpriseResourcePlanning.enums;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    USER_NOT_FOUND("The User was not found"),
    TOKEN_NOT_FOUND("The token was not found"),
    ROLE_NOT_FOUND("The Role was not found"),
    ACTION_NOT_FOUND("The Action was not found"),
    AVATAR_NOT_FOUND("The Avatar was not found"),
    MODULE_NOT_FOUND("The Module was not found"),
    CLIENT_NOT_FOUND("The Client was not found"),
    CONTRACT_NOT_FOUND("The Contract was not found"),
    ORGANIZATION_NOT_FOUND("The Organization was not found"),
    PERMISSION_NOT_FOUND("The Permission was not found"),
    PRODUCT_NOT_FOUND("The Product was not found"),
    PROJECT_NOT_FOUND("The Project was not found"),
    SALES_NOT_FOUND("The Sales was not found"),
    FILE_NOT_FOUND("The file was not found"),

    USER_IS_DEACTIVATED("The User is Deactivated so action cant be done"),
    ROLE_IS_DEACTIVATED("The Role is Deactivated so cant be used"),
    PROJECT_IS_NOT_ACCESSIBLE("The Project is in Illegal State "),

    USER_ALREADY_EXISTS("The User already exists"),
    CONTRACT_CLIENT_ALREADY_EXISTS("The Contract Client Already exists"),
    ORGANIZATION_CONTRACT_ALREADY_EXISTS("The Organization Contract Already exists"),
    USER_ORGANIZATION_ALREADY_EXISTS("The User Organization Already exists"),
    PROJECT_ORGANIZATION_ALREADY_EXISTS("The Project Organization Already exists"),
    ROLE_PERMISSION_ALREADY_EXISTS("The Role Permission Already exists"),
    PRODUCT_SALES_ALREADY_EXISTS("The Product Sales Already exists"),
    USER_ROLE_ALREADY_EXISTS("The User Role Already exists"),

    WRONG_CREDENTIALS("The Credentials are Wrong");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
