package com.ishvatov.validation.truck;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;


/**
 * Empty interface, which defines the order of the validation.
 *
 * @author Sergey Khvatov
 */
@GroupSequence({Default.class, ExistingTruckChecks.class, NoTruckOrderAssignedChecks.class})
public interface ExistingTruckChecksOrder {
}
