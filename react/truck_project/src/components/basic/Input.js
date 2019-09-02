import React, {Component} from 'react';
import PropTypes from 'prop-types';

/**
 * Basic input field component.
 * @author Sergey Hvatov
 */
class Input extends Component {

	static propTypes = {
		/** Text of the label */
		label: PropTypes.string,
		/** Placeholder of the input */
		placeholder: PropTypes.string,
		/** Type of the input  */
		type: PropTypes.string,
		/** Error message text */
		error: PropTypes.string,
		/** Function, which is called when onChange occurs */
		handleOnChange: PropTypes.func
	};

	render() {
		let {
			label, type,
			handleOnChange, placeholder,
			error
		} = this.props;
		return (
			<div>
				<div className='input-group mb-3'>
					<div className="input-group-prepend">
						<span className="input-group-text default-text">{label}</span>
					</div>
					<input className='form-control default-text'
						   type={type}
						   onChange={event => handleOnChange(event.target.value)}
						   placeholder={placeholder}/>
				</div>
				<div className="alert alert-danger error-text"
					 role="alert"
					 hidden={error === ''}>
					<strong>Error!</strong>
					<i>{error}</i>
				</div>
			</div>
		);
	}
}

export default Input;