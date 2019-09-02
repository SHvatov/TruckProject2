import {combineReducers} from 'redux'
import {credentials} from './Credentials';
import Constants from "../utils/Constants";

/**
 * Initial state of the application.
 */
export const INITIAL_STATE = {

	// user credentials
	credentials: {
		// fetching status
		isFetching: false,

		// user's credentials
		id: '',
		password: '',
		authority: Constants.NOT_AUTHORIZED,

		// user credentials input error
		error: {
			id: '',
			password: '',
			other: ''
		}
	},

	/** Describes current status of the opened tab */
	currentTab: {
		// opened category
		category: Constants.MANAGER_TRUCKS_CATEGORY,
		// fetching status
		isFetching: false
	},

	// data, which is currently stored in the
	// application state
	data: [
		/**
		 * {
		 *     entity: Object, - dto object itself
		 *     error: Object, - corresponding to this object errors
		 *     					for each editable element + other for internal errors
		 *     status: {
		 *        isEditing: boolean,
		 *        isDeleting: boolean,
		 *        isShowingDetailedInformation: boolean
		 *        isFetching
		 *     } - status of the element
		 * }
		 */
	]
};

/**
 * Application state reducers.
 */
export const ROOT_REDUCER = combineReducers({
	credentials
});
