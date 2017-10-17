self.addEventListener("push", (event) => {
  const {name, id} = event.data.json();
  event.waitUntil(
    self.registration.showNotification("Steamdating", {
      body: `New data available for ${name}`,
      data: id,
      tag: `sd-follow-${id}`,
      icon: "icons/steamdating-512.png",
      vibrate: [500, 100, 500],
    })
  );
});


self.addEventListener("notificationclick", (event) => {
  const url = `/#/follow/${event.notification.data}`;
  event.waitUntil(
    Promise.all([
      self.clients.openWindow(url),
      event.notification.close(),
    ])
  );
});
