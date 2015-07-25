package com.azmotors.store.foundation.runtime;

import com.azmotors.store.model.Constants;

public final class RuntimeInfo
{
    private RuntimeInfo()
    {
        super();
    }

    public static String provideVendorInfo()
    {
        final StringBuilder strBldr = new StringBuilder();
        strBldr.append("VM is ");
        strBldr.append(Runtime.class.getPackage().getImplementationVendor());
        strBldr.append(Constants.C_SPACE);
        strBldr.append(Runtime.class.getPackage().getImplementationVersion());
        strBldr.trimToSize();
        return strBldr.toString();
    }

    public static long deriveMemoryStatus()[]
    {
        final long freeMemoryMiB = Runtime.getRuntime().freeMemory() / 1000000;
        final long totalMemorMiB = Runtime.getRuntime().totalMemory() / 1000000;
        final long memoryInUse = totalMemorMiB - freeMemoryMiB;
        return new long[] { memoryInUse, totalMemorMiB };
    }
}
