import {combineReducers} from 'redux'
import Constants from "../utils/Constants";
import {credentials} from './Credentials';
import {error} from './Error'
import {buffer} from "./Buffer";
import {status} from "./Status";
import {fetched} from "./Fetched";
import {data} from "./Data";

/**
 * Initial state of the application.
 */
export const INITIAL_STATE = {

	/** user credentials */
	credentials: {
		id: '',
		password: '',
		authority: Constants.NOT_AUTHORIZED,
	},

	/** Describes current status of all objects.
	 * Does not need to be updated
	 * on componentDidMount
	 */
	status: {
		manager: {
			current: Constants.TRUCKS_TABLE
		}
	},

	/** Specific buffer, which is used to store buffer information,
	 * that has been fetched from the server, such as
	 * select values and etc.
	 */
	fetched: {},

	/** Describes current error.
	 * Does not need to be updated
	 * on componentDidMount */
	error: {},

	/** Buffer of the application - all inputs are here.
	 * Requires initial update on componentDidMount
	 * in order to store empty, but necessary fields.*/
	buffer: {},

	// data, which is currently stored in the
	// application state, accessed by id
	data: {
		/**
		 * {
		 *     entity: Object, - dto object itself
		 *     status: {
		 *        isEditing: boolean,
		 *        isDeleting: boolean,
		 *        isFetching: boolean,
		 *        isShowingMap: boolean
		 *     } - status of the element
		 * }
		 */
	}
};

/**
 * Application state reducers.
 */
export const ROOT_REDUCER = combineReducers({
	buffer,
	credentials,
	data,
	error,
	fetched,
	status
});
