import React, {Component} from 'react';
import Input from "../input/Input";
import {Map, Placemark, SearchControl, ZoomControl} from "react-yandex-maps";
import {connect} from "react-redux";
import PropTypes from "prop-types";
import {updateBuffer} from "../../../state/Buffer";
import {Row} from "react-bootstrap";

class CargoAddComponent extends Component {

	static propTypes = {
		// application buffer
		buffer: PropTypes.object,
		// function, called to update coordinates in teh buffer
		handleCoordinatesUpdate: PropTypes.func
	};

	render() {
		let {buffer, handleCoordinatesUpdate} = this.props;
		return (
			<div className='table-row'>
				<Input name='name' label='Description'/>
				<Input name='mass' label='Mass' postLabel='kg.'/>
				<Row>
					<Map className='map' defaultState={{
						center: [buffer['longitude'], buffer['latitude']],
						zoom: 7,
						controls: []
					}}>
						<Placemark geometry={[
							buffer['longitude'], buffer['latitude']
						]}/>
						<SearchControl options={{float: 'right'}}
									   instanceRef={control => this.searchControl = control}
									   onResultShow={() => {
										   if (this.searchControl) {
											   let coords = this.searchControl
												   .getResultsArray()[0]
												   .geometry
												   .getCoordinates();
											   handleCoordinatesUpdate(coords);
										   }
									   }}/>
						<ZoomControl options={{float: 'right'}}/>
					</Map>
				</Row>
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
		buffer: state.buffer,
	};
};

/**
 * Dispatches action creator functions to properties.
 * @param dispatch - dispatcher instance.
 */
const mapDispatchToProps = dispatch => {
	return {
		handleCoordinatesUpdate: (coords) => dispatch(updateBuffer({longitude: coords[0], latitude: coords[1]}))
	}
};

const CargoAdd = connect(mapStateToProps, mapDispatchToProps)(CargoAddComponent);

export default CargoAdd;