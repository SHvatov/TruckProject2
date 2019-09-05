/** Action, which is performed to update the buffer. */
export const UPDATE_BUFFER = 'UPDATE_BUFFER';

/** Action, which is performed to clear the buffer. */
export const CLEAR_BUFFER = 'CLEAR_BUFFER';

/**
 * Action creator, which is called to create
 * UPDATE_BUFFER action.
 * @returns {{type: *}}
 */
export function updateBuffer(value) {
	return {type: UPDATE_BUFFER, value};
}

/**
 * Action creator, which is called to create
 * CLEAR_BUFFER action.
 * @returns {{type: *}}
 */
export function clearBuffer() {
	return {type: CLEAR_BUFFER};
}

/**
 * Reducer function, which is used to process
 * buffer associated actions.
 * @param state - initial state (false).
 * @param action - current action.
 */
export function buffer(state = {}, action) {
	switch (action.type) {
		case UPDATE_BUFFER:
			return Object.assign({}, state, action.value);
		case CLEAR_BUFFER:
			return {};
		default:
			return state;
	}
}