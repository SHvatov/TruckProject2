/**
 * Class with predefined static variables, that
 * stores some common constants, that are used in
 * this app.s
 */
export default class Constants {
	/** URL of the server */
	static SERVER_URL = 'http://localhost:8080/TruckProject2_war_exploded/';

	/** Yandex maps api key */
	static YANDEX_MAPS_API_KEY = 'a1a077a0-5fde-4766-b446-28896760001b';

	/** Different types of users in the system */
	static MANAGER = 'ROLE_USER';
	static DRIVER = 'ROLE_DRIVER';
	static NOT_AUTHORIZED = 'NOT_AUTHORIZED';

	// types of request
	static POST = 'post';
	static GET = 'get';

	// server request urls
	static LOGIN = Constants.SERVER_URL + 'login/authenticate';
	static TRUCK = {
		FETCH: Constants.SERVER_URL + 'trucks/list',
		ADD: Constants.SERVER_URL + 'trucks/add',
		UPDATE: Constants.SERVER_URL + 'trucks/update',
		DELETE: Constants.SERVER_URL + 'trucks/delete',
	}
}