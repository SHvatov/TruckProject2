import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import {Provider} from 'react-redux';
import {INITIAL_STATE, ROOT_REDUCER} from './state/Reducers';
import {applyMiddleware, createStore} from 'redux';
import thunk from 'redux-thunk';
import {YMaps} from "react-yandex-maps";
import Constants from "./utils/Constants";

// redux store
const store = createStore(
	ROOT_REDUCER,
	INITIAL_STATE,
	applyMiddleware(thunk)
);

// app main
ReactDOM.render((
		<Provider store={store}>
			<YMaps query={{apikey: Constants.YANDEX_MAPS_API_KEY}}>
				<App/>
			</YMaps>
		</Provider>
	),
	document.getElementById('root')
);
