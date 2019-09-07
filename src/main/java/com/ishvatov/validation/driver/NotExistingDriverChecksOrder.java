package com.ishvatov.validation.driver;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * Empty interface, which defines the order of the validation.
 *
 * @author Sergey Khvatov
 */
@GroupSequence({Default.class, NotExistingDriverChecks.class})
public interface NotExistingDriverChecksOrder {
}
