import { createSlice } from '@reduxjs/toolkit';

export const UserSlice = createSlice({
  name: 'user',
  initialState: {
    userDetails: null,
  },
  reducers: {
    setUserDetails: (state, action) => {
      state.userDetails = Object.assign({}, action.payload);
    },
    removeUserDetails: (state, action) => {
      state.userDetails = null;
      localStorage.removeItem('token');
    },
  },
});

export const { setUserDetails, removeUserDetails } = UserSlice.actions;

export default UserSlice.reducer;
