package com.ishvatov.validation.cargo;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * Empty interface, which defines the order of the validation.
 *
 * @author Sergey Khvatov
 */
@GroupSequence({Default.class, ExistingCargoChecks.class, NoCargoOrderAssignedChecks.class})
public interface ExistingCargoChecksOrder {
}
