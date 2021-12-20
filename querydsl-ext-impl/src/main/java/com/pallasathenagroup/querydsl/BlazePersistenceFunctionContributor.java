package com.pallasathenagroup.querydsl;

import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.blazebit.persistence.spi.CriteriaBuilderConfigurationContributor;
import com.blazebit.persistence.spi.JpqlFunctionGroup;

/**
 * Ensure that the custom Hibernate functions are registered with the appropriate function kind,
 * as aggregate expressions require special handling in Blaze-Persistence (for example during
 * implicit group by generation).
 *
 * @author Jan-Willem Gmelig Meyling
 * @since 2.1.0
 */
public class BlazePersistenceFunctionContributor implements CriteriaBuilderConfigurationContributor {

    @Override
    public void contribute(CriteriaBuilderConfiguration config) {
        config.registerFunction(new JpqlFunctionGroup("CAST_YEARMONTH", false));
        config.registerFunction(new JpqlFunctionGroup("CAST_MONTH", false));
        config.registerFunction(new JpqlFunctionGroup("CAST_YEAR", false));
        config.registerFunction(new JpqlFunctionGroup("RANGE_OVERLAPS", false));
        config.registerFunction(new JpqlFunctionGroup("RANGE_CONTAINS", false));
        config.registerFunction(new JpqlFunctionGroup("RANGE_IS_CONTAINED_BY", false));
        config.registerFunction(new JpqlFunctionGroup("RANGE_STRICTLY_LEFT_OF", false));
        config.registerFunction(new JpqlFunctionGroup("RANGE_STRICTLY_RIGHT_OF", false));
        config.registerFunction(new JpqlFunctionGroup("RANGE_ADJACENT_TO", false));
        config.registerFunction(new JpqlFunctionGroup("RANGE_UNION", false));
        config.registerFunction(new JpqlFunctionGroup("RANGE_INTERSECTION", false));
        config.registerFunction(new JpqlFunctionGroup("RANGE_DIFFERENCE", false));
        config.registerFunction(new JpqlFunctionGroup("RANGE_LOWER_BOUND", false));
        config.registerFunction(new JpqlFunctionGroup("RANGE_UPPER_BOUND", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_OVERLAPS", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_CONTAINS", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_CONTAINS_ELEMENT", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_IS_CONTAINED_BY", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_CONCAT", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_APPEND", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_PREPEND", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_NDIMS", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_DIMS", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_FILL", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_LENGTH", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_TOSTRING", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_UNNEST", false));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_AGG", true));
        config.registerFunction(new JpqlFunctionGroup("ARRAY_ELEMENT_AT", false));
        config.registerFunction(new JpqlFunctionGroup("DURATION_BETWEEN", false));
        config.registerFunction(new JpqlFunctionGroup("DURATION_ADD", false));
        config.registerFunction(new JpqlFunctionGroup("DURATION_SUBTRACT", false));
        config.registerFunction(new JpqlFunctionGroup("DURATION_MULTIPLY", false));
        config.registerFunction(new JpqlFunctionGroup("DURATION_DIVIDE", false));
        config.registerFunction(new JpqlFunctionGroup("DURATION_AVG", true));
        config.registerFunction(new JpqlFunctionGroup("DURATION_MAX", true));
        config.registerFunction(new JpqlFunctionGroup("DURATION_MIN", true));
        config.registerFunction(new JpqlFunctionGroup("DURATION_SUM", true));
        config.registerFunction(new JpqlFunctionGroup("PERIOD_BETWEEN", false));
        config.registerFunction(new JpqlFunctionGroup("PERIOD_ADD", false));
        config.registerFunction(new JpqlFunctionGroup("PERIOD_SUBTRACT", false));
        config.registerFunction(new JpqlFunctionGroup("PERIOD_MULTIPLY", false));
        config.registerFunction(new JpqlFunctionGroup("PERIOD_DIVIDE", false));
        config.registerFunction(new JpqlFunctionGroup("PERIOD_AVG", true));
        config.registerFunction(new JpqlFunctionGroup("PERIOD_MAX", true));
        config.registerFunction(new JpqlFunctionGroup("PERIOD_MIN", true));
        config.registerFunction(new JpqlFunctionGroup("PERIOD_SUM", true));
        config.registerFunction(new JpqlFunctionGroup("JSON_CONTAINS_KEY", false));
        config.registerFunction(new JpqlFunctionGroup("JSON_GET", false));
        config.registerFunction(new JpqlFunctionGroup("JSON_GET_TEXT", false));
        config.registerFunction(new JpqlFunctionGroup("JSON_CONCAT", false));
    }

}
