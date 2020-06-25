import Vue from 'vue';
import Toasted from 'vue-toasted'

Vue.use(Toasted, {
   iconPack: 'material',
   position: 'top-center',
   duration: 2500,
   singleton: true,
});

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

 Vue.toasted.register('emptyLocation', ' Please select a location!', {
   type: 'error',
   icon: 'error_outline',
 })




 // Global errors
 Vue.toasted.register('loginError', 'Wrong Email or password!', {
   type: 'error',
   icon: 'error_outline',
 })

 Vue.toasted.register('unknownError', 'Something went wrong!', {
   type: 'error',
   icon: 'report_problem',
 })