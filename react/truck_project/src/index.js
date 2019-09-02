import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import {Provider} from 'react-redux';
import {INITIAL_STATE, ROOT_REDUCER} from './state/Reducers';
import {applyMiddleware, createStore} from 'redux';
import thunk from 'redux-thunk';
import Selector from "./components/basic/Selector";

const store = createStore(ROOT_REDUCER, INITIAL_STATE, applyMiddleware(thunk));

ReactDOM.render((
		/*<Provider store={store}>
			<App/>
		</Provider>
	),*/
		<div className='row centered-element'>
			<Selector label='Test' error='' options={[{label: 'test1', value: 'test1'}]}/>
		</div>
	),
		document.getElementById('root')
);