/** Action, which is performed to update the buffer. */
export const UPDATE_FETCHED_BUFFER = 'UPDATE_FETCHED_BUFFER';

/** Action, which is performed to clear the buffer. */
export const CLEAR_FETCHED_BUFFER = 'CLEAR_FETCHED_BUFFER';

/**
 * Action creator, which is called to create
 * UPDATE_FETCHED_BUFFER action.
 * @returns {{type: *}}
 */
export function updateFetched(value) {
	return {type: UPDATE_FETCHED_BUFFER, value};
}

/**
 * Action creator, which is called to create
 * CLEAR_FETCHED_BUFFER action.
 * @returns {{type: *}}
 */
export function clearFetched() {
	return {type: CLEAR_FETCHED_BUFFER};
}

/**
 * Reducer function, which is used to process
 * buffer associated actions.
 * @param state - initial state (false).
 * @param action - current action.
 */
export function fetched(state = {}, action) {
	switch (action.type) {
		case UPDATE_FETCHED_BUFFER:
			return Object.assign({}, state, action.value);
		case CLEAR_FETCHED_BUFFER:
			return {};
		default:
			return state;
	}
}