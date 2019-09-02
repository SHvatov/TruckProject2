/**
 * Class with predefined static variables, that
 * stores the status codes of all errors,
 * that are being processed by this app.
 */
export default class RequestErrorCodes {
	static BAD_REQUEST = 400;
	static FORBIDDEN = 403;
	static NOT_FOUND = 404;
	static INTERNAL_SERVER_ERROR = 500;
}