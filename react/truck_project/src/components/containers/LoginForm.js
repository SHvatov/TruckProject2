import React, {Component} from 'react';
import PropTypes from 'prop-types';
import Button from '../basic/Button';
import Form from "../basic/Form";
import {updatePassword, updateId, validatingCredentials} from "../../state/Credentials";
import {connect} from "react-redux";
import Constants from "../../utils/Constants";

/**
 * Basic login form component.
 * @author Sergey Hvatov
 */
class LoginFormComponent extends Component {

	static propTypes = {
		/** Defines, whether the request is being processed */
		isFetching: PropTypes.bool,
		credentials: PropTypes.shape({
			id: PropTypes.string,
			username: PropTypes.string,
		}),
		/** Object with errors, that may occur during validation */
		error: PropTypes.shape({
			id: PropTypes.string,
			password: PropTypes.string,
			other: PropTypes.string
		}),
		/** Function, which is called to
		 *  create UPDATE_USERNAME action */
		handleUsernameUpdate: PropTypes.func,
		/** Function, which is called to
		 *  create UPDATE_PASSWORD action */
		handlePasswordUpdate: PropTypes.func,
		/** Function, which is called to
		 *  create VALIDATE_CREDENTIALS action */
		validateCredentials: PropTypes.func
	};

	render() {
		let {
			isFetching, credentials,
			error, handleUsernameUpdate,
			handlePasswordUpdate, validateCredentials
		} = this.props;
		return (
			<div>
				<div className='login-form'>
					<div className='row centered-element'>
						<div className='col'
							 style={{
								 fontSize: '37px',
								 marginTop: '5px',
								 marginBottom: '40px',
								 background: 'whitesmoke',
								 borderRadius: '100px'
							 }}>
							Truck Project v2.0
						</div>
					</div>
					<Form type={Constants.VERTICAL_FROM}
						  params={[{
							  label: 'Username',
							  handleOnChange: handleUsernameUpdate,
							  error: error.id
						  }, {
							  label: 'Password',
							  type: 'password',
							  handleOnChange: handlePasswordUpdate,
							  error: error.password
						  }]}/>
					<div className='row centered-element element-margin'>
						<div className='col'>
							<Button text='Login'
									isFetching={isFetching}
									handleOnClick={() => validateCredentials(
										Constants.SERVER_URL, {
											id: credentials.id,
											password: credentials.password
										})
									}
									clazz='btn btn-primary btn-lg'/>
						</div>
					</div>
					<div className='row centered-element element-margin'>
						<div className='col'>
							<div className="alert alert-danger error-text"
								 hidden={error.other === ''}>
								<strong>Error!</strong>
								<i>{error.other}</i>
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	}
}

/**
 * Maps state state to react properties.
 * @param state - state instance.
 */
const mapStateToProps = state => {
	return {
		isFetching: state.credentials.isFetching,
		credentials: {
			id: state.credentials.id,
			password: state.credentials.password,
		},
		error: state.credentials.error
	};
};

/**
 * Dispatches action creator functions to properties.
 * @param dispatch - dispatcher instance.
 */
const mapDispatchToProps = dispatch => {
	return {
		handleUsernameUpdate: id => dispatch(updateId(id)),
		handlePasswordUpdate: password => dispatch(updatePassword(password)),
		validateCredentials: (url, user) => dispatch(validatingCredentials(url, user))
	}
};

const LoginForm = connect(mapStateToProps, mapDispatchToProps)(LoginFormComponent);

export default LoginForm;
