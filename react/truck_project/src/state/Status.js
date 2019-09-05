/** Action, which is performed to update the errors list */
export const UPDATE_STATUS_BY_ID = 'UPDATE_STATUS_BY_ID';

/** Action, which is performed to clear the errors. */
export const CLEAR_STATUS_BY_ID = 'CLEAR_STATUS_BY_ID';

/**
 * Action creator, which is called to create
 * CLEAR_STATUS_BY_ID action.
 * @param id - id of the entity, whose status is going to be updated.
 * @returns {{type: *, id: *}}
 */
export function clearStatusById(id) {
	return {type: CLEAR_STATUS_BY_ID, id};
}

/**
 * Action creator, which is called to create
 * UPDATE_STATUS_BY_ID action.
 * @param id - id of the entity, whose status is going to be updated.
 * @param status - new status.
 * @returns {{type: *, id: *, status: *}}
 */
export function updateStatusById(id, status) {
	return {type: UPDATE_STATUS_BY_ID, id, status};
}

/**
 * Reducer function, which is used to process
 * error associated actions.
 * @param state - initial state (empty object).
 * @param action - current action.
 */
export function status(state = {}, action) {
	switch (action.type) {
		case UPDATE_STATUS_BY_ID:
			return Object.assign({}, state, {
				[action.id]: Object.assign({}, state[action.id], action.status)
			});
		case CLEAR_STATUS_BY_ID:
			return Object.assign({}, state, {
				[action.id]: {}
			});
		default:
			return state;
	}
}