import React, {Component} from 'react';
import Input from "../input/Input";
import Select from "../input/Select";
import {Map, Placemark, SearchControl, ZoomControl} from "react-yandex-maps";
import {connect} from "react-redux";
import PropTypes from "prop-types";
import {updateBuffer} from "../../../state/Buffer";

class TruckAddComponent extends Component {

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
				<Input name='id' label='Reg. Number'/>
				<Input name='capacity' label='Capacity'/>
				<Input name='shiftSize' label='Shift Size'/>
				<Select label='Status' name='status'
						options={[
							{label: 'In order', value: 'IN_ORDER'},
							{label: 'Not in order', value: 'NOT_IN_ORDER'}]
						}/>
				<div>
					<Map defaultState={{
						center: [55, 37],
						zoom: 7,
						controls: []
					}} modules={[
						'geoObject.addon.balloon',
						'geoObject.addon.hint'
					]} className='map'>
						<Placemark properties={{hintContent: 'New position'}}
								   geometry={[
									   buffer['longitude'] ? buffer['longitude'] : 55,
									   buffer['latitude'] ? buffer['latitude'] : 37
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

const TruckAdd = connect(mapStateToProps, mapDispatchToProps)(TruckAddComponent);

export default TruckAdd;