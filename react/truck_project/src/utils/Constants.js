/**
 * Class with predefined static variables, that
 * stores some common constants, that are used in
 * this app.s
 */
import {generateRandomId} from "./Utils";

export default class Constants {
	/** URL of the server */
	static SERVER_URL = 'http://localhost:8080/TruckProject2_war_exploded/';

	/** Yandex maps api key */
	static YANDEX_MAPS_API_KEY = 'a1a077a0-5fde-4766-b446-28896760001b';

	static TRUCKS_TABLE = 'trucks_table';
	static DRIVERS_TABLE = 'drivers_table';
	static CARGO_TABLE = 'cargo_table';
	static ORDERS_TABLE = 'order_table';

	/** Different types of users in the system */
	static MANAGER_USER = 'ROLE_USER';
	static DRIVER_USER = 'ROLE_DRIVER';
	static NOT_AUTHORIZED = 'NOT_AUTHORIZED';

	// types of request
	static POST = 'post';
	static GET = 'get';

	// initial values for the entities
	static TRUCK_ENTITY_INIT_VALUE = {
		id: generateRandomId(2, 5),
		capacity: 1000,
		shiftSize: 8,
		status: 'IN_ORDER',
		longitude: 55,
		latitude: 37
	};
	static DRIVER_ENTITY_INIT_VALUE = {
		id: generateRandomId(5, 5),
		name: '',
		surname: '',
		password: generateRandomId(5, 5),
		workedHours: 0,
		lastUpdated: new Date(),
		longitude: 55,
		latitude: 37,
		status: 'IDLE'
	};
	static CARGO_ENTITY_INIT_VALUE = {
		name: '',
		mass: '0',
		longitude: 55,
		latitude: 37,
		status: 'READY'
	};

	// server request urls
	static LOGIN = Constants.SERVER_URL + 'login/authenticate';
	static TRUCK = {
		FETCH: Constants.SERVER_URL + 'manager/trucks/list',
		ADD: Constants.SERVER_URL + 'manager/trucks/add',
		UPDATE: Constants.SERVER_URL + 'manager/trucks/update',
		DELETE: Constants.SERVER_URL + 'manager/trucks/delete',
	};
	static DRIVER = {
		FETCH: Constants.SERVER_URL + 'manager/drivers/list',
		ADD: Constants.SERVER_URL + 'manager/drivers/add',
		UPDATE: Constants.SERVER_URL + 'manager/drivers/update',
		DELETE: Constants.SERVER_URL + 'manager/drivers/delete',
	};
	static CARGO = {
		FETCH: Constants.SERVER_URL + 'manager/cargo/list',
		ADD: Constants.SERVER_URL + 'manager/cargo/add',
		UPDATE: Constants.SERVER_URL + 'manager/cargo/update',
		DELETE: Constants.SERVER_URL + 'manager/cargo/delete',
	};
}