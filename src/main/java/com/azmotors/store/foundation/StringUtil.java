package com.azmotors.store.foundation;

public final class StringUtil
{
    private StringUtil()
    {
        super();
    }

    public static String composeString(final String... args)
    {
        assert null != args : "Parameter 'args' of method 'composeString' must not be null";
        final StringBuilder strBuilder = new StringBuilder();
        for (final String next : args)
        {
            strBuilder.append(next);
        }
        strBuilder.trimToSize();
        return strBuilder.toString();
    }

    public static boolean isEmpty(final String str)
    {
        return null == str || str.trim().isEmpty();
    }
}
