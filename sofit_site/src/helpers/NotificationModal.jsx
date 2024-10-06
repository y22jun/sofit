import React from 'react';
import './NotificationModal.scss';

const NotificationModal = ({ isOpen, onClose }) => {
    const handleClose = () => {
        onClose(); // Call the onClose function to close the modal
    };

    return (
        <div className={`notification-modal ${isOpen ? 'open' : ''}`}>
            <div className="modal-content">
                <div className="modal-header">
                    <h2>Notifications</h2>
                    <button className="close-btn" onClick={handleClose}>Close</button>
                </div>
                <div className="modal-body">
                    {/* Add your notification items here */}
                    <div className="notification-item">
                        <span>1: Notification Content 1</span>
                    </div>
                    <div className="notification-item">
                        <span>2: Notification Content 2</span>
                    </div>
                    {/* Add more notification items as needed */}
                </div>
            </div>
        </div>
    );
};

export default NotificationModal;
