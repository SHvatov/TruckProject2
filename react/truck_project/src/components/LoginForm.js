import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {validateCredentialsRequest} from "../state/Requests";
import {connect} from "react-redux";
import {Alert, Col, Form, FormGroup} from "react-bootstrap";
import Input from "./containers/input/Input";
import SubmitButton from "./containers/input/SubmitButton";
import {updateBuffer} from "../state/Buffer";

/**
 * Basic login form component.
 * @author Sergey Hvatov
 */
class LoginFormComponent extends Component {

	static propTypes = {
		/** Defines, whether the request is being processed */
		status: PropTypes.object,
		// application buffer
		buffer: PropTypes.object,
		/** Object with errors, that may occur during validation */
		error: PropTypes.object,
		// Handles the validation of buffer
		validateCredentials: PropTypes.func,
		// initializes buffer with empty buffer
		initializeBuffer: PropTypes.func
	};

	componentDidMount() {
		// update the buffer in order to store
		// the latest information about buffer
		this.props.initializeBuffer();
	}

	render() {
		let {
			status, buffer,
			error, validateCredentials
		} = this.props;
		return (
			<Form className='login-form'>
				<Form.Row className="justify-content-md-center">
					<div className='login-form-hello-text'>
						Truck Project v2.0
					</div>
				</Form.Row>
				<Input type='text' name='id'
					   label='Username'/>
				<Input type='password' name='password'
					   label='Password'/>
				<Form.Row>
					<Alert variant="danger" hidden={!error['loginError']}>
						<strong>Error: </strong>{error['loginError']}
					</Alert>
				</Form.Row>
				<Form.Row className={'justify-content-center'}>
					<FormGroup as={Col} className={'text-center'}>
						<SubmitButton isFetching={status ? status['fetch'] : false} text='Send'
									  onClickHandler={() => validateCredentials({
										  id: buffer['id'],
										  password: buffer['password']
									  })}/>
					</FormGroup>
				</Form.Row>
			</Form>
		);
	}
}

/**
 * Maps state state to react properties.
 * @param state - state instance.
 */
const mapStateToProps = state => {
	return {
		status: state.status['login'],
		buffer: state.buffer,
		error: state.error,
	};
};

/**
 * Dispatches action creator functions to properties.
 * @param dispatch - dispatcher instance.
 */
const mapDispatchToProps = dispatch => {
	return {
		validateCredentials: (buffer) => dispatch(validateCredentialsRequest(buffer)),
		initializeBuffer: () => dispatch(updateBuffer({id: '', password: ''}))
	}
};

const LoginForm = connect(mapStateToProps, mapDispatchToProps)(LoginFormComponent);

export default LoginForm;
