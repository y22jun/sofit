const ProtectedRoute = () => {
  const isAuthenticated  = localStorage.getItem('isAuthenticated');

  if (!Boolean(isAuthenticated)) {
    // return window.location.replace('/login');
  }
};

export default ProtectedRoute;
