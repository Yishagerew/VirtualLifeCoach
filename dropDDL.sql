ALTER TABLE activities DROP FOREIGN KEY FK_activities_MeasureDefId
ALTER TABLE careperson DROP FOREIGN KEY FK_careperson_CareGiverId
ALTER TABLE careperson DROP FOREIGN KEY FK_careperson_PersonId
ALTER TABLE measuredefinitionrange DROP FOREIGN KEY FK_measuredefinitionrange_MeasureDefId
ALTER TABLE measurehistory DROP FOREIGN KEY FK_measurehistory_PersonGoalId
ALTER TABLE personalgoals DROP FOREIGN KEY FK_personalgoals_PERSONGOALID
ALTER TABLE personalgoals DROP FOREIGN KEY FK_personalgoals_PersonId
ALTER TABLE personalgoals DROP FOREIGN KEY FK_personalgoals_MeasureDefId
ALTER TABLE reminders DROP FOREIGN KEY FK_reminders_PersonId
DROP TABLE activities
DROP TABLE caregivers
DROP TABLE careperson
DROP TABLE currentmeasure
DROP TABLE measuredefinition
DROP TABLE measuredefinitionrange
DROP TABLE measurehistory
DROP TABLE person
DROP TABLE personalgoals
DROP TABLE reminders
