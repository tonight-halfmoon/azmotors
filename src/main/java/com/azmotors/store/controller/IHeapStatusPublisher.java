package com.azmotors.store.controller;

public interface IHeapStatusPublisher
{
    void publish(final long memoryStatus[] );
}
