import React, { useState, useEffect } from 'react';
import './Subomission.scss';
import Apis from '../../../api/Apis';

const Subomission = ({ checkpointId }) => {
    const [weeks, setWeeks] = useState([]);
    const [selectedWeek, setSelectedWeek] = useState(1);

    useEffect(() => {
        fetchWeeklyInfo();
    }, []);

    const fetchWeeklyInfo = async () => {
        try {
            const response = await Apis.get(`/weeoklyinfo/${checkpointId}`);
            const weekData = response.data.data;
            console.log('주차 정보', weekData);

            const newWeeks = weekData.map((week, index) => ({
                id: week.id,
                name: `${index + 1}주차`,
                assignments: [],
            }));

            setWeeks(newWeeks);

            if (newWeeks.length > 0) {
                setSelectedWeek(newWeeks[0].id);
                await fetchAssignInfo(newWeeks[0].id);
            }
        } catch (error) {
            console.error('Error fetching weekly info:', error);
        }
    };

    const fetchAssignInfo = async (weeklyId) => {
        try {
            console.log(`Fetching assignments for week: ${weeklyId}`);
            const response = await Apis.get(`/${weeklyId}/assignment`);
            const assignmentsData = response.data.data;
            console.log(`주차 ${weeklyId}의 과제 정보`, assignmentsData);
            const assIds = assignmentsData.map(assignment => assignment.assId);
            console.log(`주차 ${weeklyId}의 과제 정보의 assId 배열:`, assIds);

            const assignmentsWithSubmission = await Promise.all(
                assignmentsData.map(async (assignment) => {
                    try {
                        const submissionResponse = await Apis.get(`/${assignment.assId}/subofassignment`);
                        const submissionData = submissionResponse.data.data;
                        console.log(`Assignment ID ${assignment.assId} submission data:`, submissionData);
                        // Assume submissionData is an array of submissions sorted by date
                        const latestSubmission = submissionData.length > 0 ? submissionData[0] : null;
                        return {
                            ...assignment,
                            submitted: latestSubmission ? latestSubmission.isOk : false,
                            filePath: latestSubmission ? latestSubmission.filePath : null,
                        };
                    } catch (error) {
                        console.error(`Error fetching submission for assignment ${assignment.assId}:`, error);
                        return {
                            ...assignment,
                            submitted: false,
                            filePath: null,
                        };
                    }
                })
            );

            setWeeks((prevWeeks) =>
                prevWeeks.map((week) =>
                    week.id === weeklyId ? { ...week, assignments: assignmentsWithSubmission } : week
                )
            );
        } catch (error) {
            console.error(`Error fetching assignments for week ${weeklyId}:`, error);
        }
    };

    const selectWeek = async (weekId) => {
        setSelectedWeek(weekId);
        await fetchAssignInfo(weekId);
    };

    console.log()

    const handleFileChange = async (weekId, assignmentId, file) => {
        console.log(weekId, assignmentId, file)
        try {
            const formData = new FormData();
            formData.append('file', file);

            const response = await Apis.post(`/subofassignment/${assignmentId}`, formData, {
                headers: { 'Content-Type': 'multipart/form-data' },
            });
            const filePath = response.data.filePath;

            console.log('File upload response:', response.data);
            console.log('File path:', filePath);

            const updatedWeeks = weeks.map((week) => {
                if (week.id === weekId) {
                    const updatedAssignments = week.assignments.map((assignment) => {
                        if (assignment.assId === assignmentId) {
                            return { ...assignment, submitted: true, filePath: filePath };
                        }
                        return assignment;
                    });
                    return { ...week, assignments: updatedAssignments };
                }
                return week;
            });
            setWeeks(updatedWeeks);
        } catch (error) {
            console.error('Error submitting file:', error);
        }
    };

    const calculateProgress = (weekId) => {
        const selectedWeek = weeks.find((week) => week.id === weekId);

        if (!selectedWeek) {
            console.error('Week not found.');
            return 0;
        }

        const totalAssignments = selectedWeek.assignments.length;
        const submittedAssignments = selectedWeek.assignments.filter((assignment) => assignment.submitted).length;
        const progress = (submittedAssignments / totalAssignments) * 100 || 0;

        return progress;
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
                        </div>
                    ))}
                </div>
            </div>
            <div className="assignment-section">
                <h2>{selectedWeekName} 주차 과제</h2>
                <div className="assignments">
                    {weeks.find((week) => week.id === selectedWeek)?.assignments.map((assignment, index) => (
                        <div key={index} className="assignment-item">
                            <span>{assignment.title}</span>
                            {assignment.submitted ? (
                                <div>
                                    <span>제출 완료</span>
                                    <a href={assignment.filePath} target="_blank" rel="noopener noreferrer">다운로드</a>
                                </div>
                            ) : (
                                <input
                                    type="file"
                                    onChange={(e) =>
                                        handleFileChange(selectedWeek, assignment.assId, e.target.files[0])
                                    }
                                />
                            )}
                        </div>
                    ))}
                </div>
                <div className="progress-bar">
                    <div
                        className="progress"
                        style={{ width: `${calculateProgress(selectedWeek)}%` }}
                    ></div>
                </div>
            </div>
        </div>
    );
};

export default Subomission;
