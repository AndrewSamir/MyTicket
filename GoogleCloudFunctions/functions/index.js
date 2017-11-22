
var functions = require('firebase-functions');
const admin = require('firebase-admin');
// const  fetch = require('node-fetch');


admin.initializeApp(functions.config().firebase);

exports.changeChairState = functions.database
    .ref('/services/{serviceId}/events/{eventName}/times/{time}/{chairID}/state').onWrite(event => {


        // Exit when the data is deleted.
        if (!event.data.exists()) {
            console.log('second');

            return;
        }

        var eventSnapshot = event.data;

        var state = eventSnapshot.val();


        if (state == 2 || state == 1) {

            admin.database().ref('/services/' + event.params.serviceId + "/events/" + event.params.eventName + "/times/" +
                event.params.time + "/" + event.params.chairID).once('value').then(function (snapshot) {
                    var reservedBy = snapshot.child('reservedBy').val();
                    var method = snapshot.child('paymentMethod').val();
                    var reservedByName = snapshot.child('name').val();
                    var mobile = snapshot.child('mobile').val();

                    console.log('body:', reservedBy);

                    const myTicket = {
                        serviceId: event.params.serviceId,
                        eventName: event.params.eventName,
                        eventTime: event.params.time,
                        reservedChair: event.params.chairID,
                        paymentMethod: method,
                        reserveName: reservedByName,
                        mobile: mobile,
                        state: state.toString()
                    };
                    const myURI = admin.database().ref('/users/' + reservedBy + "/tickets/" +
                        event.params.eventName + event.params.time + event.params.chairID + "/");

                    myURI.set(myTicket);

                    if (state == 1) {

                        var message = 'لابد من تأكيد الحجز فى خلال 24 ساعه'
                    } else if (state == 2) {
                        var message = 'تم تأكيد الحجز'

                    }

                    const payLoad = {
                        data: {
                            title: 'My Ticket',
                            text: message,
                            sound: 'default',
                            eventName: event.params.eventName,
                            eventTime: event.params.time,
                            reservedChair: event.params.chairID,
                            paymentMethod: method,
                            state: state.toString()
                        }
                    };

                    const options = {
                        priority: "high",
                        timeToLive: 60 * 60 * 2
                    };

                    admin.messaging().sendToTopic(reservedBy, payLoad, options);

                });



            return;
        }




    });
