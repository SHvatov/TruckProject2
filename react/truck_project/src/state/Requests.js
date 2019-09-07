import axios from 'axios';
import RequestErrorCodes from "../utils/RequestErrorCodes";
import {clearBuffer} from "./Buffer";
import {clearError, updateError} from "./Error";
import {clearStatusById, updateStatusById} from "./Status";
import Constants from "../utils/Constants";
import {validateCredentialsRequestSuccess} from "./Credentials";
import {addEntitySuccess, deleteEntitySuccess, fetchEntitiesSuccess, updateEntitySuccess} from "./Data";

/**
 * Dispatches multiple cations at once.
 * @param actions - array of actions to dispatch.
 * @returns {function(*): *}
 */
export function multipleActions(actions = []) {
	return (dispatch) => actions.map(a => dispatch(a));
}

/**
 * Sends post request to the login/authenticate
 * in order to validate credentials of the user.
 * @param credentials - user's credentials.
 */
export const validateCredentialsRequest = (credentials) => {
	return (dispatch) => {
		// pre-operations
		dispatch(updateStatusById('login', {fetch: true}));
		// clear errors
		dispatch(clearError());

		// request
		axios({
			method: Constants.POST,
			url: Constants.LOGIN,
			data: credentials
		}).then((response) => {
			// set request status
			dispatch(updateStatusById('login', {fetch: false}));
			// clear input buffer
			dispatch(clearBuffer());
			// clear errors
			dispatch(clearError());
			// update the credentials
			dispatch(validateCredentialsRequestSuccess({
				...credentials,
				authority: response.data['authority']
			}));
		}).catch((error) => {
			// update error object in case of exception
			let err;
			if (error.response) {
				// set error text on BAD REQUEST
				if (error.response.status === RequestErrorCodes.BAD_REQUEST) {
					err = error.response.data;
				} else if (error.response.status === RequestErrorCodes.FORBIDDEN) {
					err = {loginError: 'Can not access server using your credentials'};
				} else {
					err = {loginError: error.response.statusText};
				}
			} else if (error.request) {
				err = {loginError: 'The request was made but no response was received.'};
			}

			// set request status
			dispatch(updateStatusById('login', {fetch: false}));
			// update error
			dispatch(updateError(err));
		});
	};
};

/**
 * Sends get request to the server in order to fetch
 * the list of trucks entities.
 * @param url - request path.
 * @param credentials - user's auth credentials.
 */
export const fetchEntityList = (url, credentials) => {
	return (dispatch) => {
		// pre-operations
		dispatch(updateStatusById('table', {fetch: true}));
		// clear errors
		dispatch(clearError());

		// request
		axios({
			method: Constants.GET,
			url: url,
			auth: {
				username: credentials.id,
				password: credentials.password
			}
		}).then((response) => {
			// set request status
			dispatch(updateStatusById('table', {fetch: false}));
			// clear input buffer
			dispatch(clearBuffer());
			// clear errors
			dispatch(clearError());
			// update the credentials
			dispatch(fetchEntitiesSuccess(response.data));
		}).catch((error) => {
			// update error object in case of exception
			let err;
			if (error.response) {
				// set error text on BAD REQUEST
				if (error.response.status === RequestErrorCodes.BAD_REQUEST) {
					err = error.response.data;
				} else if (error.response.status === RequestErrorCodes.FORBIDDEN) {
					err = {tableError: 'Can not access server using your credentials'};
				} else {
					err = {tableError: error.response.data};
				}
			} else if (error.request) {
				err = {tableError: 'The request was made but no response was received.'};
			}

			// set request status
			dispatch(updateStatusById('table', {fetch: false}));
			// update error
			dispatch(updateError(err));
		});
	};
};

/**
 * Sends get request to the server in order to add
 * new entity to the system.
 * @param url - request path.
 * @param entity - new entity to add.
 * @param credentials - user's auth credentials.
 */
export const addEntity = (url, entity, credentials) => {
	return (dispatch) => {
		// pre-operations
		dispatch(updateStatusById('addModal', {fetch: true, add: true}));
		// clear errors
		dispatch(clearError());

		// request
		axios({
			method: Constants.POST,
			url: url,
			auth: {
				username: credentials.id,
				password: credentials.password
			},
			data: entity
		}).then((response) => {
			// set request status
			dispatch(updateStatusById('addModal', {fetch: false, add: false}));
			// clear input buffer
			dispatch(clearBuffer());
			// clear errors
			dispatch(clearError());
			// update the credentials
			dispatch(addEntitySuccess(response.data['id'], response.data));
		}).catch((error) => {
			// update error object in case of exception
			let err;
			if (error.response) {
				// set error text on BAD REQUEST
				if (error.response.status === RequestErrorCodes.BAD_REQUEST) {
					err = error.response.data;
				} else if (error.response.status === RequestErrorCodes.FORBIDDEN) {
					err = {addModalError: 'Can not access server using your credentials'};
				} else {
					err = {addModalError: error.response.data};
				}
			} else if (error.request) {
				err = {addModalError: 'The request was made but no response was received.'};
			}

			// set request status
			dispatch(updateStatusById('addModal', {fetch: false}));
			// update error
			dispatch(updateError(err));
		});
	};
};

/**
 * Sends get request to the server in order to add
 * new entity to the system.
 * @param url - request path.
 * @param entity - new entity to add.
 * @param credentials - user's auth credentials.
 */
export const updateEntity = (url, entity, credentials) => {
	return (dispatch) => {
		// pre-operations
		dispatch(updateStatusById(entity['id'], {fetch: true, edit: true}));
		// clear errors
		dispatch(clearError());

		// request
		axios({
			method: Constants.POST,
			url: url,
			auth: {
				username: credentials.id,
				password: credentials.password
			},
			data: entity
		}).then((response) => {
			// set request status
			dispatch(updateStatusById(entity['id'], {fetch: false, edit: false}));
			// clear input buffer
			dispatch(clearBuffer());
			// clear errors
			dispatch(clearError());
			// update the credentials
			dispatch(updateEntitySuccess(response.data['id'], response.data));
		}).catch((error) => {
			// update error object in case of exception
			let err;
			if (error.response) {
				// set error text on BAD REQUEST
				if (error.response.status === RequestErrorCodes.BAD_REQUEST) {
					err = error.response.data;
				} else if (error.response.status === RequestErrorCodes.FORBIDDEN) {
					err = {tableError: 'Can not access server using your credentials'};
				} else {
					err = {tableError: error.response.data};
				}
			} else if (error.request) {
				err = {tableError: 'The request was made but no response was received.'};
			}

			// set request status
			dispatch(updateStatusById(entity['id'], {fetch: false}));
			// update error
			dispatch(updateError(err));
		});
	};
};

/**
 * Sends get request to the server in order to delete
 * entity from the system.
 * @param url - request path.
 * @param id - id of the entity to delete.
 * @param credentials - user's auth credentials.
 */
export const deleteEntity = (url, id, credentials) => {
	return (dispatch) => {
		// pre-operations
		dispatch(updateStatusById(id, {fetch: true, del: true}));
		// clear errors
		dispatch(clearError());

		// request
		axios({
			method: Constants.POST,
			url: url,
			auth: {
				username: credentials.id,
				password: credentials.password
			},
			data: {id}
		}).then((response) => {
			// set request status
			dispatch(clearStatusById(id));
			// clear input buffer
			dispatch(clearBuffer());
			// clear errors
			dispatch(clearError());
			// update the credentials
			dispatch(deleteEntitySuccess(response.data));
		}).catch((error) => {
			// update error object in case of exception
			let err;
			if (error.response) {
				// set error text on BAD REQUEST
				if (error.response.status === RequestErrorCodes.BAD_REQUEST) {
					err = error.response.data;
				} else if (error.response.status === RequestErrorCodes.FORBIDDEN) {
					err = {tableError: 'Can not access server using your credentials'};
				} else {
					err = {tableError: error.response.data};
				}
			} else if (error.request) {
				err = {tableError: 'The request was made but no response was received.'};
			}

			// set request status
			dispatch(updateStatusById(id, {fetch: false}));
			// update error
			dispatch(updateError(err));
		});
	};
};