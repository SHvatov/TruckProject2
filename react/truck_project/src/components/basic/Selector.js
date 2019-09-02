import React, {Component} from 'react';
import PropTypes from 'prop-types';

/**
 * Basic select component.
 * @author Sergey Hvatov
 */
class Selector extends Component {

	static propTypes = {
		/** Label of the select element */
		label: PropTypes.string,
		/** Error message text */
		error: PropTypes.string,
		/** Array of elements to select */
		options: PropTypes.arrayOf(PropTypes.shape({
				label: PropTypes.string,
				value: PropTypes.string
			}
		))
	};

	render() {
		let {
			label, options,
			error, handleOnChange
		} = this.props;
		return (
			<div>
				<div className='input-group mb-3'>
					<div className="input-group-prepend">
						<span className="input-group-text default-text">{label}</span>
					</div>
					<select className='custom-select default-text'
							onChange={}>
						{
							options.map((opt, index) => (
								<option value={opt.value} key={index}>
									{opt.label}
								</option>
							))
						}
					</select>
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

export default Selector;