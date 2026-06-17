package com.sicredi.credito.application.factory;

import com.sicredi.credito.application.strategy.SegmentoCreditoStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SegmentoCreditoFactoryTest {

    @Test
    void getStrategy_returnsCorrectStrategy() {
        SegmentoCreditoStrategy sA = mock(SegmentoCreditoStrategy.class);
        when(sA.getSegmento()).thenReturn("A");

        SegmentoCreditoStrategy sB = mock(SegmentoCreditoStrategy.class);
        when(sB.getSegmento()).thenReturn("B");

        SegmentoCreditoFactory factory = new SegmentoCreditoFactory(Arrays.asList(sA, sB));

        assertSame(sA, factory.getStrategy("A"));
        assertSame(sB, factory.getStrategy("B"));
    }

    @Test
    void getStrategy_returnsNullForUnknownKey() {
        SegmentoCreditoStrategy sA = mock(SegmentoCreditoStrategy.class);
        when(sA.getSegmento()).thenReturn("A");

        SegmentoCreditoFactory factory = new SegmentoCreditoFactory(Arrays.asList(sA));

        assertNull(factory.getStrategy("UNKNOWN"));
        assertNull(factory.getStrategy(null));
    }
}