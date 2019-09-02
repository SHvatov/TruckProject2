package com.ishvatov.validation.user;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * Empty interface, which defines the order of the validation.
 *
 * @author Sergey Khvatov
 */
@GroupSequence({Default.class, NotExistingUserChecks.class})
public interface NotExistingUserChecksOrder {
}
