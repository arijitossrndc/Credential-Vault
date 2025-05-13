package com.epam.pwt.Config;

final public class RegexConstants {

	public static final String GROUP_NAME_PATTERN = "^[A-Za-z]*$";
    public static final String ACCOUNT_NAME_PATTERN = "^[a-z_]*$";

    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9]" +
            "([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])"
    		+ "(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    
    public static  final String URL_PATTERN= "(((http|https)://)(www.)?"
            + "[a-zA-Z0-9@:%._\\+~#?&//=]"
            + "{2,256}\\.[a-z]"
            + "{2,6}\\b([-a-zA-Z0-9@:%"
            + "._\\+~#?&//=]*)|(^$))";

}

