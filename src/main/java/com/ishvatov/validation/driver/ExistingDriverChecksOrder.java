package com.ishvatov.validation.driver;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * Empty interface, which defines the order of the validation.
 *
 * @author Sergey Khvatov
 */
@GroupSequence({Default.class, ExistingDriverChecks.class, NoDriverOrderAssignedChecks.class})
public interface ExistingDriverChecksOrder {
}
