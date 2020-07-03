import Vue from 'vue';
import Toasted from 'vue-toasted'

Vue.use(Toasted, {
   iconPack: 'material',
   position: 'top-center',
   duration: 2500,
   singleton: true,
});


// Info messages
Vue.toasted.register('loggedOut', 'You have successfully logged out.', {
   type: 'info',
   icon: 'info',
 })
 
 Vue.toasted.register('alreadyLoggedIn', 'You are logged in. Please log out to continue.', {
   type: 'info',
   icon: 'info',
 })

 Vue.toasted.register('successMessage', 'Success!', {
   type: 'success',
   icon: 'done_outline',
 })


 // Input errors
 Vue.toasted.register('emptyLoginFields', 'Email and password cannot be empty!', {
   type: 'error',
   icon: 'error_outline',
 })

 Vue.toasted.register('startDateAfterEndDate', 'Start date cannot be after end date!', {
   type: 'error',
   icon: 'error_outline',
 })

 Vue.toasted.register('emptyDates', 'Please select dates!', {
   type: 'error',
   icon: 'error_outline',
 })

 Vue.toasted.register('emptyGuests', 'Please select number of guests!', {
   type: 'error',
   icon: 'error_outline',
 })

 Vue.toasted.register('emptyFields', 'Please fill in all the fields!', {
   type: 'error',
   icon: 'error_outline',
 })

 Vue.toasted.register('emptyLocation', 'Please select a location!', {
   type: 'error',
   icon: 'error_outline',
 })

 Vue.toasted.register('badConfirmPassword', 'Password and confirm password must match!', {
   type: 'error',
   icon: 'error_outline',
 })




 // Authorization errors
 Vue.toasted.register('loginError', 'Wrong Email or password!', {
   type: 'error',
   icon: 'error_outline',
 })

 Vue.toasted.register('noPermission', 'You do not have permission to access this page!', {
   type: 'error',
   icon: 'error_outline',
 })

 Vue.toasted.register('signupError', 'Username already exists!', {
   type: 'error',
   icon: 'error_outline',
 })



  // Global errors
 Vue.toasted.register('unknownError', 'Something went wrong!', {
   type: 'error',
   icon: 'report_problem',
 })