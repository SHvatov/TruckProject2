/** Action, which is performed to update the errors list */
export const UPDATE_ERROR = 'UPDATE_ERROR';

/** Action, which is performed to clear the errors. */
export const CLEAR_ERROR = 'CLEAR_ERROR';

/**
 * Action creator, which is called to create
 * CLEAR_ERROR action.
 * @returns {{type: *}}
 */
export function clearError() {
	return {type: CLEAR_ERROR};
}

/**
 * Action creator, which is called to create
 * UPDATE_ERROR action.
 * @param error - error object.
 * @returns {{type: *, error: *}}
 */
export function updateError(error) {
	return {type: UPDATE_ERROR, error};
}

/**
 * Reducer function, which is used to process
 * error associated actions.
 * @param state - initial state (empty object).
 * @param action - current action.
 */
export function error(state = {}, action) {
	switch (action.type) {
		case UPDATE_ERROR:
			return action.error;
		case CLEAR_ERROR:
			return {};
		default:
			return state;
	}
}