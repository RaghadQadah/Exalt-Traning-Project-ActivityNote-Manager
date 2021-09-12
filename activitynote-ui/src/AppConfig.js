const AppConfig = {
    // for production mode, this should be empty ''
    host: 'http://localhost:8082'
}

const ApiUrls = {
    notesListUrl: `${AppConfig.host}/notes`,
    noteUrl: `${AppConfig.host}/note`,
    locationUrl: `${AppConfig.host}/location`,
    activityListUrl: `${AppConfig.host}/activity`,
    notificationsUrl: `${AppConfig.host}/notifications`
}

export {AppConfig, ApiUrls}
