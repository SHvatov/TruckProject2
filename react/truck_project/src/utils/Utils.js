import {Col} from "react-bootstrap";
import React from "react";

/**
 * Checks if passed object is object or null.
 * @param obj - object to check.
 * @returns {boolean} true, if obj is object, false otherwise.
 */
export const isObject = (obj) => (
	typeof obj === 'object'
);

/**
 * Checks if passed object is object or null.
 * @param obj - object to check.
 * @returns {boolean} true, if obj is object, false otherwise.
 */
export const isEmpty = (obj) => (
	obj ? Object.keys(obj).length === 0 : false
);

/**
 * Checks if passed object is a null reference.
 * @param obj - object to check.
 * @returns {boolean} true, if obj is null, false otherwise.
 */
export const isNull = (obj) => (
	obj === null
);

/**
 * Converts this data array of objects into object,
 * where it's properties are the id's of the entities,
 * that are stored in the object.
 * @param data - array of entities with id property.
 * @return {{[id]: *}}
 */
export const convertArrayIntoObjectById = (data = []) => {
	let obj = {};
	// eslint-disable-next-line no-unused-vars
	for (let entity of data) {
		obj[entity.id] = entity;
	}
	return obj;
};

/**
 * Converts given array into array of columns.
 * @param arr - array to convert.
 * @returns array of Col elements.
 */
export const convertArrayToList = (arr) => (
	arr.map((elem, index) => (
		<li key={index}>
			{elem}
		</li>
	))
);

/**
 * Converts given object into array of columns.
 * @param obj - object to convert.
 * @param postfix - object with postfix for specified fields.
 * @returns array of Col elements.
 */
export const convertObjectToColumns = (obj = {}, postfix = {}) => (
	Object.keys(obj).map(key => {
		if (isNull(obj[key])) {
			return (
				<Col className='table-col' key={key}>
					<div className='centered-text'>
						Not assigned.
					</div>
				</Col>
			);
		} else if (Array.isArray(obj[key])) {
			return (
				<Col className='table-col' key={key}>
					{convertArrayToList(obj[key])}
				</Col>
			);
		} else if (isObject(obj[key])) {
			return convertObjectToColumns(obj[key]);
		} else {
			return (
				<Col className='table-col' key={key}>
					<div className='centered-text'>
						{obj[key] + ' ' + (key in postfix ? postfix[key] : '')}
					</div>
				</Col>
			);
		}
	})
);