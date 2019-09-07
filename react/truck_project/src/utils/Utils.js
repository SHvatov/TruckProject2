import {Col} from "react-bootstrap";
import React from "react";

/**
 * Checks if passed object is undefined or null.
 * @param obj - object to check.
 * @returns {boolean} true, if obj is object, false otherwise.
 */
export const isUndefined = (obj) => (
	typeof obj === 'undefined'
);

/**
 * Checks if passed object is object or null.
 * @param obj - object to check.
 * @returns {boolean} true, if obj is object, false otherwise.
 */
export const isObject = (obj) => (
	typeof obj === 'object'
);

export const isEmptyObject = (obj) => (
	obj ? Object.keys(obj).length === 0 : false
);

/**
 * Checks if passed object is object or null.
 * @param obj - object to check.
 * @returns {boolean} true, if obj is object, false otherwise.
 */
export const isEmpty = (obj) => (
	obj ? Array.isArray(obj) && obj.length === 0 : false
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
		if (isNull(obj[key]) || isEmpty(obj[key])) {
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
			let norm = obj[key].toString().replace(new RegExp('_', 'g'), ' ');
			if (norm.length > 20) {
				return (<div></div>);
			} else {
				return (
					<Col className='table-col' key={key}>
						<div className='centered-text'>
							{norm + ' ' + (key in postfix ? postfix[key] : '')}
						</div>
					</Col>
				);
			}
		}
	})
);

/**
 * Generates random integer from min to max.
 * @param min - minimum value.
 * @param max - maximum value.
 * @returns {number} random number from min to max.
 */
export const randomInteger = (min, max) => {
	let rand = min + Math.random() * (max + 1 - min);
	return Math.floor(rand);
};

/**
 * Generates random id for the entity.
 * @param charsNum - number of chars in the id.
 * @param digitsNum - number of digits in the id.
 * @returns {string} random id for the entity.
 */
export const generateRandomId = (charsNum, digitsNum) => {
	let result = '';
	let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
	let charactersLength = characters.length;
	for (let i = 0; i < charsNum; i++) {
		result += characters.charAt(Math.floor(Math.random() * charactersLength));
	}
	for (let i = 0; i < digitsNum; i++) {
		result += randomInteger(0, 9).toString();
	}
	return result;
}