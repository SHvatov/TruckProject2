/** Action, which is performed when validation
 * request to the server succeeds. */
export const VALIDATE_CREDENTIALS_REQUEST_SUCCESS = 'VALIDATE_CREDENTIALS_REQUEST_SUCCESS';

/**
 * Action creator, which is called to create
 * VALIDATE_CREDENTIALS_REQUEST_SUCCESS action.
 * @param credentials - user's credentials.
 * @returns {{authority: *, type: *}}
 */
export function validateCredentialsRequestSuccess(credentials) {
	return {
		type: VALIDATE_CREDENTIALS_REQUEST_SUCCESS, credentials
	};
}

/**
 * Reducer function, which is used to process
 * credentials associated actions.
 * @param state - initial state (empty object).
 * @param action - current action.
 */
export function credentials(state = {}, action) {
	switch (action.type) {
		case VALIDATE_CREDENTIALS_REQUEST_SUCCESS:
			return Object.assign({}, state, action.credentials);
		default:
			return state;
	}
}