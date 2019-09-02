import React, {Component} from 'react';
import PropTypes from 'prop-types';
import Input from "./Input";
import Constants from "../../utils/Constants";

/**
 * Basic input form component.
 * @author Sergey Hvatov
 */
class Form extends Component {

	static propTypes = {
		/** Type of the form - horizontal or vertical */
		type: PropTypes.oneOf([Constants.VERTICAL_FROM, Constants.HORIZONTAL_FROM]),
		/** Array of input elements, with is used to init Input component */
		params: PropTypes.array
	};

	render() {
		let {type, params} = this.props;
		let inputList = params.map((inputParams, index) =>
			<div className='col' key={index}>
				<Input label={inputParams.label || ''}
					   type={inputParams.type || 'text'}
					   handleOnChange={inputParams.handleOnChange}
					   placeholder={inputParams.placeholder || ''}
					   error={inputParams.error || ''}/>
			</div>
		);

		switch (type) {
			default:
			case Constants.HORIZONTAL_FROM:
				return (
					<form>
						<div className='row centered-element'>
							{inputList}
						</div>
					</form>
				);
			case Constants.VERTICAL_FROM:
				return (
					<form>
						<div>
							{
								inputList.map((inputElement, index) => (
									<div className='row centered-element' key={index}>{inputElement}</div>)
								)
							}
						</div>
					</form>
				);
		}
	}
}

export default Form;