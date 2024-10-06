import React, { useState, useEffect } from 'react';
import './WeekAssignment.scss';
import Apis from '../../../api/Apis';

const WeekAssignment = ({ checkpointId }) => {
    const [weeks, setWeeks] = useState([]);
    const [selectedWeek, setSelectedWeek] = useState(1);
    const [progress, setProgress] = useState({});
    const [newAssignmentName, setNewAssignmentName] = useState('');

    useEffect(() => {
        fetchWeeklyInfo();
    }, []);

    const fetchWeeklyInfo = async () => {
        try {
            const response = await Apis.get(`/weeoklyinfo/${checkpointId}`);
            const weekData = response.data.data;
            console.log('주차 정보1', response.data);
            console.log('주차 정보2', weekData);

            // Create weeks based on the received data
            const weeks = weekData.map((week, index) => ({
                id: week.id,
                name: `${index + 1}주차`,
                assignments: []
            }));
            setWeeks(weeks);

            if (weeks.length > 0) {
                setSelectedWeek(weeks[0].id);
            }

            // Fetch assignments for each week
            for (const week of weeks) {
                await fetchAssignInfo(week.id);
            }
        } catch (error) {
            console.error('Error fetching weekly info:', error);
        }
    };

    const fetchAssignInfo = async (weeklyId) => {
        try {
            const response = await Apis.get(`/${weeklyId}/assignment`);
            const assignmentsData = response.data.data;
            console.log('과제 정보', assignmentsData);
            assignmentsData.forEach((assignment) => {
                const assignmentId = assignment.assId;
                console.log('과제 아이디:', assignmentId);
            });
            console.log(`주차 ${weeklyId}의 과제 정보`, assignmentsData);

            setWeeks((prevWeeks) =>
                prevWeeks.map((week) =>
                    week.id === weeklyId ? { ...week, assignments: assignmentsData } : week
                )
            );
        } catch (error) {
            console.error(`Error fetching assignments for week ${weeklyId}:`, error);
        }
    };

    const addWeek = async () => {
        try {
            await Apis.post(`/weekly/${checkpointId}`);
            fetchWeeklyInfo();
        } catch (error) {
            console.error('Error adding new week:', error);
        }
    };

    const deleteWeek = async (weekId) => {
        try {
            await Apis.delete(`/weekly/${weekId}`);
            fetchWeeklyInfo();
        } catch (error) {
            console.error('Error deleting week:', error);
        }
    };

    const selectWeek = (weekId) => {
        setSelectedWeek(weekId);
    };

    const addAssignment = async (weekId) => {
        if (newAssignmentName.trim() === '') return;

        try {
            // Add assignment via API call
            await Apis.post(`/assignment/${weekId}`, { title: newAssignmentName });
            // Clear the input field
            setNewAssignmentName('');
            // Refetch weekly info to update assignments
            fetchWeeklyInfo();
        } catch (error) {
            console.error('Error adding new assignment:', error);
        }
    };

    const deleteAssignment = async (assignmentId) => {
        try {
            // Delete assignment via API call
            await Apis.delete(`/assignment/${assignmentId}`);
            // Refetch weekly info to update assignments
            fetchWeeklyInfo();
        } catch (error) {
            console.error('Error deleting assignment:', error);
        }
    };

    const selectedWeekName = weeks.find((week) => week.id === selectedWeek)?.name || `${selectedWeek}주차`;

    return (
        <div className="week-assignment-container">
            <div className="week-list-container">
                <div className="week-list">
                    {weeks.map((week) => (
                        <div
                            key={week.id}
                            className={`week-item ${week.id === selectedWeek ? 'selected' : ''}`}
                            onClick={() => selectWeek(week.id)}
                        >
                            {week.name}
                            <button className="delete-week-button" onClick={() => deleteWeek(week.id)}>삭제</button>
                        </div>
                    ))}
                </div>
                <button className="add-week-button" onClick={addWeek}>주차 추가</button>
            </div>
            <div className="assignment-section">
                <h2>{selectedWeekName} 과제</h2>

                {weeks.find((week) => week.id === selectedWeek)?.assignments.map((assignment) => (
                    <div key={assignment.assId} className="assignment-item">
                        <span>{assignment.title}</span>
                        <button className="delete-assignment-button" onClick={() => deleteAssignment(assignment.assId)}>삭제</button>
                    </div>
                ))}

                <div className="new-assignment-container">
                    <input
                        type="text"
                        className="assignment-input"
                        value={newAssignmentName}
                        onChange={(e) => setNewAssignmentName(e.target.value)}
                        placeholder="Enter assignment name"
                    />
                    <button className="add-assignment-button" onClick={() => addAssignment(selectedWeek)}>과제 추가</button>
                </div>

                <div className="progress-bar">
                    <div
                        className="progress"
                        style={{ width: `${progress[selectedWeek] || 0}%` }}
                    ></div>
                    <div className="progress-text">
                        {Math.round(progress[selectedWeek] || 0)}%
                    </div>
                </div>
            </div>
        </div>
    );
};

export default WeekAssignment;
