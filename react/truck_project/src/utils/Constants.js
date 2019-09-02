/**
 * Class with predefined static variables, that
 * stores some common constants, that are used in
 * this app.s
 */
export default class Constants {
	/** URL of the server */
	static SERVER_URL = 'http://localhost:8080/TruckProject2_war_exploded/';

	/** Different types of users in the system */
	static MANAGER = 'ROLE_USER';
	static DRIVER = 'ROLE_DRIVER';
	static NOT_AUTHORIZED = 'NOT_AUTHORIZED';

	/** Different categories, that are available for the user */
	static MANAGER_TRUCKS_CATEGORY = 'trucks';
	static MANAGER_DRIVERS_CATEGORY = 'drivers';
	static MANAGER_CARGO_CATEGORY = 'cargo';
	static MANAGER_ORDERS_CATEGORY = 'orders';

	/** Form types */
	static VERTICAL_FROM = 'vertical';
	static HORIZONTAL_FROM = 'horizontal';
}