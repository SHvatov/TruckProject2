/** Action, which is performed when username is updated through input. */
import axios from 'axios';
import RequestErrorCodes from "../utils/RequestErrorCodes";
import Constants from "../utils/Constants";

export const UPDATE_USERNAME = 'UPDATE_USERNAME';

/** Action, which is performed when username is updated through input. */
export const UPDATE_PASSWORD = 'UPDATE_PASSWORD';

/** Action, which is performed when validation
 * request to the server is being performed. */
export const VALIDATE_CREDENTIALS_REQUEST = 'VALIDATE_CREDENTIALS_REQUEST';

/** Action, which is performed when validation
 * request to the server fails with error. */
export const VALIDATE_CREDENTIALS_ERROR = 'VALIDATE_CREDENTIALS_ERROR';

/** Action, which is performed when validation
 * request to the server succeeds. */
export const VALIDATE_CREDENTIALS_SUCCESS = 'VALIDATE_CREDENTIALS_SUCCESS';

/**
 * Action creator, which is called to create
 * UPDATE_USERNAME action.
 * @param id - updated through input username.
 * @returns {{type: *, username: *}}
 */
export function updateId(id) {
	return {
		type: UPDATE_USERNAME, id
	};
}

/**
 * Action creator, which is called to create
 * UPDATE_PASSWORD action.
 * @param password - updated through input password.
 * @returns {{password: *, type: *}}
 */
export function updatePassword(password) {
	return {
		type: UPDATE_PASSWORD, password
	};
}

/**
 * Action creator, which is called to create
 * VALIDATE_CREDENTIALS_REQUEST action.
 * @returns {{type: *}}
 */
export function validateCredentialsRequest() {
	return {
		type: VALIDATE_CREDENTIALS_REQUEST
	};
}

/**
 * Action creator, which is called to create
 * VALIDATE_CREDENTIALS_ERROR action.
 * @param error - error object, with message for corresponding
 * fields.
 * @returns {{type: *, error: *}}
 */
export function validateCredentialsError(error) {
	return {
		type: VALIDATE_CREDENTIALS_ERROR, error
	};
}

/**
 * Action creator, which is called to create
 * VALIDATE_CREDENTIALS_SUCCESS action.
 * @param authority - authority of the user, requested from the server.
 * @returns {{authority: *, type: *}}
 */
export function validateCredentialsSuccess(authority) {
	return {
		type: VALIDATE_CREDENTIALS_SUCCESS, authority
	};
}

/**
 * Async function, which sends POST request to the server
 * in order to validate user's credentials.
 * @param path - path to the server.
 * @param credentials - user's credentials to validate.
 * @returns {Function}
 */
export function validatingCredentials(path, credentials) {
	return (dispatch) => {
		dispatch(validateCredentialsRequest());
		axios({
			method: 'post',
			url: path + 'login/authenticate',
			data: credentials
		}).then((response) => {
			// update current authority, then call callback function
			// to update the state of the parent component
			dispatch(validateCredentialsSuccess(response.data['authority']))
		}).catch((error) => {
			// if response error occurred
			let err;
			if (error.response) {
				// set error text on BAD REQUEST
				if (error.response.status === RequestErrorCodes.BAD_REQUEST) {
					err = {
						id: 'id' in error.response.data ? error.response.data['id'] : '',
						password: 'password' in error.response.data ? error.response.data['password'] : '',
						other: ''
					};
				} else {
					err = {
						id: '',
						password: '',
						other: error.response.statusText
					};
				}
			} else if (error.request) {
				err = {
					id: '',
					password: '',
					other: 'The request was made but no response was received'
				}
			}
			dispatch(validateCredentialsError(err));
		});
	};
}

/**
 * Initial state of the user.
 * @type {{password: string, authority: string, username: string}}
 */
const initialState = {
	// fetching status
	isFetching: false,

	// user's credentials
	id: '',
	password: '',
	authority: Constants.NOT_AUTHORIZED,

	// error
	error: {
		id: '',
		password: '',
		other: ''
	}
};

/**
 * Reducer function, which is used to process
 * {UPDATE_PASSWORD, UPDATE_USERNAME} actions.
 * @param state - initial state (empty object).
 * @param action - current action.
 */
export function credentials(state = initialState, action) {
	switch (action.type) {
		case UPDATE_USERNAME:
			return Object.assign({}, state, {
				id: action.id
			});
		case UPDATE_PASSWORD:
			return Object.assign({}, state, {
				password: action.password
			});
		case VALIDATE_CREDENTIALS_REQUEST:
			return Object.assign({}, state, {
				isFetching: true,
				error: initialState.error
			});
		case VALIDATE_CREDENTIALS_SUCCESS:
			return Object.assign({}, state, {
				isFetching: false,
				authority: action.authority,
				error: initialState.error
			});
		case VALIDATE_CREDENTIALS_ERROR:
			return Object.assign({}, state, {
				isFetching: false,
				error: action.error
			});
		default:
			return state;
	}
}