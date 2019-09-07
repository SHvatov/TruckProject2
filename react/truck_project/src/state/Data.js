/** This action is performed when list of entities
 * has been successfully fetched from the server.
 */
import {convertArrayIntoObjectById} from "../utils/Utils";

/** This action is performed when entities
 * have been successfully fetched.
 */
export const FETCH_ENTITIES_SUCCESS = 'FETCH_ENTITIES_SUCCESS';

/** This action is performed when entity
 * has been successfully deleted.
 */
export const DELETE_ENTITY_SUCCESS = 'DELETE_ENTITY';

/** This action is performed when entity
 * has been successfully updated through user's input.
 */
export const UPDATE_ENTITY_SUCCESS = 'UPDATE_ENTITY';

/** This action is performed when entity
 * has been successfully added through user's input.
 */
export const ADD_ENTITY_SUCCESS = 'ADD_ENTITY';

/** This action is called to clear all current data.
 */
export const CLEAR_DATA = 'CLEAR_DATA';

/**
 * Action creator, which is called to create
 * DELETE_ENTITY_SUCCESS action.
 * @param id - id of the entity that has been deleted.
 */
export function deleteEntitySuccess(id) {
	return {type: DELETE_ENTITY_SUCCESS, id};
}

/**
 * Action creator, which is called to create
 * UPDATE_ENTITY_SUCCESS action.
 * @param id - id of the entity that has been updated.
 * @param entity - updated entity object.
 */
export function updateEntitySuccess(id, entity = {}) {
	return {type: UPDATE_ENTITY_SUCCESS, id, entity};
}

/**
 * Action creator, which is called to create
 * ADD_ENTITY_SUCCESS action.
 * @param id - id of the entity that has been added.
 * @param entity - added entity object.
 */
export function addEntitySuccess(id, entity = {}) {
	return {type: ADD_ENTITY_SUCCESS, id, entity};
}

/**
 * Action creator, which is called to create
 * FETCH_ENTITIES_SUCCESS action.
 * @param entityList - list of entities, that has been fetched
 * from the server.
 */
export function fetchEntitiesSuccess(entityList = []) {
	return {type: FETCH_ENTITIES_SUCCESS, entityList};
}

/**
 * Action creator, which is called to create
 * CLEAR_DATA action.
 */
export function clearData() {
	return {type: CLEAR_DATA};
}

/**
 * Reducer function, which is used to process
 * data associated actions.
 * @param state - initial state (empty object).
 * @param action - current action.
 */
export function data(state = {}, action) {
	switch (action.type) {
		case FETCH_ENTITIES_SUCCESS:
			return convertArrayIntoObjectById(action.entityList);
		case ADD_ENTITY_SUCCESS:
		case UPDATE_ENTITY_SUCCESS:
			return Object.assign({}, state, {[action.id]: action.entity});
		case DELETE_ENTITY_SUCCESS:
			const {[action.id]: deletedEntity, ...copy} = state;
			return copy;
		case CLEAR_DATA:
			return {};
		default:
			return state;
	}
}