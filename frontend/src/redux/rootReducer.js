import { combineReducers } from '@reduxjs/toolkit';
import authReducer from './slices/authSlice';
import userReducer from './slices/userSlice';
import accountReducer from './slices/accountSlice';
import receiptReducer from './slices/receiptSlice';
import linkedAccountReducer from './slices/linkedAccount';

const rootReducer = combineReducers({
  auth: authReducer,
  user: userReducer,
  account: accountReducer,
  receipt: receiptReducer,
  linkedAccount: linkedAccountReducer,
});

export default rootReducer;